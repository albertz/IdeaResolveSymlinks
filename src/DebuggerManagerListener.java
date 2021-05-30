import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerManagerListener;
import org.jetbrains.annotations.Nullable;

public class DebuggerManagerListener implements XDebuggerManagerListener {

    @Override
    public void currentSessionChanged(@Nullable XDebugSession previousSession, @Nullable XDebugSession currentSession) {
        if(currentSession != null)
            currentSession.addSessionListener(new DebugStackFrameChangedListener(currentSession.getProject()));
    }
}
