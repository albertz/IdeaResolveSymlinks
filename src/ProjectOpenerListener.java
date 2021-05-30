import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.xdebugger.XDebuggerManager;
import org.jetbrains.annotations.NotNull;


public class ProjectOpenerListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        project.getMessageBus().connect().subscribe(
            XDebuggerManager.TOPIC,
            new DebuggerManagerListener());
    }
}
