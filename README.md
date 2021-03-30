Situation:

You click on some file which is a symlink
to some project files.
IntelliJ IDEA / PyCharm doesn't recognize this,
and opens the file as outside the project,
even in the case it is already opened.

There are a number of related bug reports,
such as:
[#256182](https://youtrack.jetbrains.com/issue/IDEA-256182),
[#141650](https://youtrack.jetbrains.com/issue/IDEA-141650)

This IDEA plugin resolves at least the open issues,
by always resolving all symlinks.
