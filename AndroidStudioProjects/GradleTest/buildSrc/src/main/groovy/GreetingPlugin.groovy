import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {

    @Override
    void apply(Project pProject) {
        def extension = pProject.extensions.create('greet', GreetExtension)
        pProject.task('hello') {
            doLast {
                println("aaa = ${extension.aaa}  bbb = ${extension.bbb}")
            }
        }

    }
}