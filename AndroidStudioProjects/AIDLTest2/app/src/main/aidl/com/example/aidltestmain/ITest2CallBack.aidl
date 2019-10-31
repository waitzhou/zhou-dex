// ITest2CallBack.aidl
package com.example.aidltestmain;

// Declare any non-default types here with import statements

interface ITest2CallBack {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            void getResultSuccess(String s);

            void getResultFail(String msg);
}
