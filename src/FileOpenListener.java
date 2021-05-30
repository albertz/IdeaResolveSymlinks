import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerManager;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.frame.XStackFrame;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public class FileOpenListener implements FileEditorManagerListener {

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        if(file.isInLocalFileSystem()) {
            Path path = Path.of(file.getPath());
            Path realPath = null;
            try {
                realPath = path.toRealPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(realPath != null) {
                if(!realPath.toString().equals(path.toString())) {
                    System.out.println("Symlink resolved: " + path + " -> " + realPath);
                    VirtualFile realPathFile = file.getFileSystem().findFileByPath(realPath.toString());
                    if (realPathFile != null) {
                        XDebuggerManager mgr = XDebuggerManager.getInstance(source.getProject());
                        XDebugSession session = mgr.getCurrentSession();
                        if(session != null) {
                            XStackFrame frame = session.getCurrentStackFrame();
                            if (frame != null) {
                                XSourcePosition pos = frame.getSourcePosition();
                                if(pos != null) {
                                    VirtualFile posFile = pos.getFile();
                                    if(posFile.equals(file)) {
                                        // We should not get here
                                        // when the DebugStackFrameChangedListener correctly handles this...
                                        System.out.println("File corresponds to current stack frame, ignore.");
                                        return;
                                    }
                                }
                            }
                        }

                        source.closeFile(file);
                        source.openFile(realPathFile, true);
                    }
                }
            }
        }
    }

}
