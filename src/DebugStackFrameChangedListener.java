import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebugSessionListener;
import com.intellij.xdebugger.XDebuggerManager;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.frame.XStackFrame;
import com.intellij.xdebugger.impl.XSourcePositionImpl;


import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;

public class DebugStackFrameChangedListener implements XDebugSessionListener {

    private final Project myProject;

    public DebugStackFrameChangedListener(Project project) {
        myProject = project;
    }

    @Override
    public void stackFrameChanged() {
        XDebuggerManager mgr = XDebuggerManager.getInstance(myProject);
        XDebugSession session = mgr.getCurrentSession();
        if(session != null) {
            XStackFrame frame = session.getCurrentStackFrame();
            if(frame != null) {
                XSourcePosition pos = frame.getSourcePosition();
                if(pos != null) {
                    VirtualFile file = pos.getFile();
                    Path path = Path.of(file.getPath());
                    Path realPath = null;
                    try {
                        realPath = path.toRealPath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(realPath != null) {
                        if (!realPath.toString().equals(path.toString())) {
                            System.out.println("Stack frame pos " + pos + " symlink resolved -> " + realPath);
                            VirtualFile realPathFile = file.getFileSystem().findFileByPath(realPath.toString());
                            try {
                                Field f = XSourcePositionImpl.class.getDeclaredField("myFile");
                                f.setAccessible(true);
                                f.set(pos, realPathFile);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }
}
