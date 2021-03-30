Situation:

You click on some file which is a symlink
to some project files.
IntelliJ IDEA / PyCharm doesn't recognize this,
and opens the file as outside the project,
even in the case it is already opened.

There are a number of related bug reports,
such as:
[#12783](https://youtrack.jetbrains.com/issue/PY-12783),
[#20544](https://youtrack.jetbrains.com/issue/PY-20544),
[#20716](https://youtrack.jetbrains.com/issue/WEB-20716),
[#39030](https://youtrack.jetbrains.com/issue/PY-39030),
[#49461](https://youtrack.jetbrains.com/issue/WI-49461),
[#82863](https://youtrack.jetbrains.com/issue/IDEA-82863),
[#141650](https://youtrack.jetbrains.com/issue/IDEA-141650),
[#204031](https://youtrack.jetbrains.com/issue/IDEA-204031),
[#256182](https://youtrack.jetbrains.com/issue/IDEA-256182)

This IDEA plugin resolves at least the open issues,
by always resolving all symlinks.
