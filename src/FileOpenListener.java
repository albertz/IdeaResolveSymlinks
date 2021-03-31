import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public class FileOpenListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        project.getMessageBus().connect().subscribe(
                FileEditorManagerListener.FILE_EDITOR_MANAGER,
                new FileEditorManagerListener() {
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
                                System.out.println("Symlink resolved: " + path + " -> " + realPath);
                                if(!realPath.toString().equals(path.toString())) {
                                    source.closeFile(file);
                                    VirtualFile realPathFile = file.getFileSystem().findFileByPath(realPath.toString());
                                    if (realPathFile != null)
                                        source.openFile(realPathFile, true);
                                }
                            }
                        }
                    }
                });
    }
}
