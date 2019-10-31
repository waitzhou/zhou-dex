package com.example.firstplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class FirstPlugin implements Plugin<Project>{

    @Override
    void apply(Project pProject) {
        pProject.task("TestTask"){
            pProject.beforeEvaluate {
                println("this is is My FirstPlugin!")
                def extension = pProject.extensions.create('deep', MyExtension)
                //MyExtension extension = pProject['deep']
                String a = extension.a
                String b = extension.b
                println("deep:${a},${b}")
            }
        }
        println("当前app的 appID:"+pProject['android']['defaultConfig'].applicationId)
    }
}