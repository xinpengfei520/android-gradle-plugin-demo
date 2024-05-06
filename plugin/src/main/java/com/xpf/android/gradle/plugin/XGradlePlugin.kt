package com.xpf.android.gradle.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


private const val TAG = "XGradlePlugin"
internal class XGradlePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            CommonPluginExtension.NAME_SPACE,
            CommonPluginExtension::class.java
        )

        /**
         * 扩展的配置要在 project.afterEvaluate 之后获取哦
         * 因为配置阶段完成, 才能读取参数, 且配置完成, 才能拿到所有的依赖
         */
        project.afterEvaluate {
            if (extension.printDependencies) {
                println(TAG + "已开启依赖打印")
                doPrintDependencies(project)
            } else {
                println(TAG + "已关闭依赖打印")
            }
        }
    }

    private fun doPrintDependencies(project: Project) {
        val androidExtension = project.extensions.getByType(
            AppExtension::class.java
        )
        androidExtension.applicationVariants.all {
            println(TAG + "applicationVariant.getName() = " + this.name)
            val configuration = project.configurations.getByName(this.name + "CompileClasspath")

            val androidLibs: MutableList<String> = ArrayList()
            configuration.resolvedConfiguration.lenientConfiguration.allModuleDependencies.forEach {
                val identifier = it.module.id
                androidLibs.add(identifier.group + ":" + identifier.name + ":" + identifier.version)
            }

            println("-------------- androidLibs start --------------")
            androidLibs.forEach {
                println(it)
            }
            println("-------------- androidLibs end --------------")
        }
    }
}
