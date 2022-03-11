package com.example.tdytest.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


public class SensorUtil implements SensorEventListener {
    private String TAG = getClass().getSimpleName();
    private SensorManager sensorManager;
    private Sensor gravitySensor;//重力传感器
    private Sensor linearAccelerometerSensor;//线性加速度传感器
    private Sensor gyroscopeSensor;//陀螺仪

    private long gravityLastTime = System.currentTimeMillis();//重力传感器上次检测的时间
    private long linearAccelerometerLastTime= System.currentTimeMillis();;// 线性加速度上次检测的时间
    private long gyroscopeLastTime = System.currentTimeMillis();;//陀螺仪上次检测的时间

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    // 检测的时间间隔 单位毫秒
    private int interval = 33;

    private double gravityThreshold = 15;
    private double linearAccelerationThreshold = 1;
    private double gyroscopeThreshold = 0.5;


    private OnSensorValueListener sensorValueListener;


    //SensorManager.SENSOR_DELAY_FASTEST = 4000
    //SensorManager.SENSOR_DELAY_NORMAL = 10000
    //SensorManager.SENSOR_DELAY_GAME = 10000
    //SensorManager.SENSOR_DELAY_UI = 10000
    public SensorUtil(Context mContext) {
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        setSenorType(Sensor.TYPE_GRAVITY);//重力
        setSenorType(Sensor.TYPE_LINEAR_ACCELERATION);//线性加速度
        setSenorType(Sensor.TYPE_GYROSCOPE);//陀螺仪
    }

    public interface OnSensorValueListener {
        void onSenorValue(float[] values, int type);

        void onSenorWarn(String type ,String msg);
    }

    public void setSensorValueListener(OnSensorValueListener onSensorValueListener) {
        sensorValueListener = onSensorValueListener;
    }

    //设置阈值
    public void setThreshold(float threshold, int type) {
        if (type == Sensor.TYPE_GRAVITY) {
            gravityThreshold = threshold;
        } else if (type == Sensor.TYPE_LINEAR_ACCELERATION) {
            linearAccelerationThreshold = threshold;
        } else if (type == Sensor.TYPE_GYROSCOPE) {
            gyroscopeThreshold = threshold;
        }
    }

    //设置时间间隔
    public void setInterval(String interval) {
        if (!interval.isEmpty()) {
            this.interval = Integer.parseInt(interval);
            Log.e(TAG, "时间间隔=" + interval);
        } else {
            this.interval = 0;
        }
    }

    public void setSenorType(int type) {
        if (sensorManager.getDefaultSensor(type) != null) {
            switch (type) {
                case Sensor.TYPE_GRAVITY: //使用重力传感器
                    gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION: //使用线性加速度计
                    linearAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                    break;
                case Sensor.TYPE_GYROSCOPE: //使用陀螺仪
                    gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                    break;
            }
        } else {
            Log.e(TAG, "没有该传感器" + type);
        }
    }

    public void setSensorState(boolean isOpen, int type) {
        switch (type) {
            case Sensor.TYPE_GRAVITY: //使用重力传感器
                if (isOpen) {
                    sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    sensorManager.unregisterListener(this, gravitySensor);
                }
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION: //使用线性加速度计
                if (isOpen) {
                    sensorManager.registerListener(this, linearAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    sensorManager.unregisterListener(this, linearAccelerometerSensor);
                }
                break;
            case Sensor.TYPE_GYROSCOPE: //使用陀螺仪
                if (isOpen) {
                    sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    sensorManager.unregisterListener(this, gyroscopeSensor);
                }
                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        sensorValueListener.onSenorValue(event.values, event.sensor.getType());
        if (sensor.getType() == Sensor.TYPE_GRAVITY) {//重力传感器
            //1、当x轴的值接近重力加速度时，说明设备的左边朝下。
            //2、当x轴的值接近负的g值时，说明设备的右边朝下。
            //3、当y轴的值接近g值时，说明设备的下边超下（与上图一样）。
            //4、当y轴的值接近负的g值时，说明设备的上边朝下（倒置）。
            //5、当z轴的值接近g值时，说明设备的屏幕朝上。
            //6、当z轴的值接近负的g值时，说明设备屏幕朝下。
            handleGravitySensor(event);
        }   else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {//线性加速度计

            handleLinearAccelerometerSensor(event);
        } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {   //陀螺仪
            //逆时针方向旋转为正
            //坐标系与加速度传感器使用的坐标系相同
            handleGyroscopeSensor(event);
        }
    }

    //重力处理函数
    private void handleGravitySensor(SensorEvent event) {

        long currentUpdateTime = System.currentTimeMillis();
        long timeInterval = currentUpdateTime - gravityLastTime;

        //getMaxDelay=20000
        //getMinDelay=10000

        if (interval > 0 && timeInterval < interval){
            return;
        }
//        MyLogUtil.e(TAG, "时间间隔 handleGravitySensor=" + timeInterval);
        gravityLastTime = currentUpdateTime;
        // 获得x,y,z坐标
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double localG = Math.sqrt(x * x + y * y + z * z);

        double Ya = Math.acos(x / localG) / Math.PI * 180;
        double Yb = Math.acos(y / localG) / Math.PI * 180;
        double Yc = Math.acos(z / localG) / Math.PI * 180;

//        MyLogUtil.e(TAG, "重力传感器  x="+x);
//        MyLogUtil.e(TAG, "重力传感器  y="+y);
//        MyLogUtil.e(TAG, "重力传感器  z="+z);
//        MyLogUtil.e(TAG, "重力传感器  localG="+localG);
//        MyLogUtil.e(TAG, "重力传感器  Ya="+Ya);
//        MyLogUtil.e(TAG, "重力传感器  Yb="+Yb);
//        MyLogUtil.e(TAG, "重力传感器  Yc="+Yc);
        if (!(Math.abs(Ya) < gravityThreshold || Math.abs(Yb) < gravityThreshold || Math.abs(Yc) < gravityThreshold)) {
//            MyLogUtil.e(TAG, "重力传感器 旋转超过15度");
            sensorValueListener.onSenorWarn("gravity","sensor_gravity_warn");
        }

    }



    //线性加速度处理函数
    private void handleLinearAccelerometerSensor(SensorEvent event) {
        long currentUpdateTime = System.currentTimeMillis();
        long timeInterval = currentUpdateTime - linearAccelerometerLastTime;

        //getMaxDelay=20000
        //getMinDelay=10000

        if (interval > 0 && timeInterval < interval){
            return;
        }
//        MyLogUtil.e(TAG, "时间间隔 handleLinearAccelerometerSensor=" + timeInterval);
        linearAccelerometerLastTime = currentUpdateTime;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        double diff = Math.sqrt(x * x + y * y + z * z);
//        MyLogUtil.e(TAG, "线性加速度传感器  x=" + x);
//        MyLogUtil.e(TAG, "线性加速度传感器  y=" + y);
//        MyLogUtil.e(TAG, "线性加速度传感器  z=" + z);
//        MyLogUtil.e(TAG, "线性加速度传感器  diff=" + diff);
        if (diff > linearAccelerationThreshold) {
//            MyLogUtil.e(TAG, "线性加速度传感器 手机在晃动");
            sensorValueListener.onSenorWarn("linearAccelerometer","sensor_linearAccelerometer_warn");
        }

    }

    //陀螺仪处理函数
    private void handleGyroscopeSensor(SensorEvent event) {
        long currentUpdateTime = System.currentTimeMillis();
        long timeInterval = currentUpdateTime - gyroscopeLastTime;

        //getMinDelay=4000
        //getMaxDelay=200000
        if (interval > 0 && timeInterval < interval){
            return;
        }
//        MyLogUtil.e(TAG, "时间间隔 handleGyroscopeSensor=" + timeInterval);

        gyroscopeLastTime = currentUpdateTime;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        double diff = Math.sqrt(x * x + y * y + z * z);
        if (diff > gyroscopeThreshold) {
//            MyLogUtil.e(TAG, "陀螺仪传感器 手机在晃动");
            sensorValueListener.onSenorWarn("gyroscope","sensor_linearAccelerometer_warn");
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResume() {
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, linearAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }
}
