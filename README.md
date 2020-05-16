# Project stats
Count source lines of code
## Options

* `--cli` Run without GUI

* `--gui` Run without CLI

* `--list` List files

* `--wait` Run without progressbar

* `--list-skipped` List skipped files and dirs

* `--list-ext=ext` List files with given extension

* `--check-dir=dir` Check if directory is skipped

* `--check-file=file` Check if file is skipped (absolute path)

* `--skip-file=file` Skip file (absolute path)*

* `--skip-dir=dir` Skip directory

* `--skip-ext=ext` Skip files with given extension

* `--no-ext` Don't show extensions

* `--help` Print help

## Example
### Program arguments
`--cli --list-skipped --skip-dir=META-INF --skip-dir=.idea`
### or
`--gui --list-skipped --skip-dir=META-INF --skip-dir=.idea`
```
scanning...
skipped: D:\JavaProjects\projstats\.git
skipped: D:\JavaProjects\projstats\.idea
...
name: projstats
size: 27,37 KiB / 28,03 KB
files: 20
folders: 25
lines total: 809
empty lines: 114
constants: [TYPE_VARIABLES, IS_SKIPPED, IS_NOT_SKIPPED, OUTPUT_FILE_NAME_FOR_COMMENTS]
type of variables: [int, boolean, String, long]
not empty lines: 695
skipped files: 9
extensions: 
  java (7)
  lst (3)
  xml (2)
  txt (1)
  [none] (1)
  gitignore (1)
  md (1)
  yml (1)
  mf (1)
  iml (1)
  properties (1)

Process finished with exit code 0

```
![GUI](https://github.com/evil-granny/ProjectStatistic/blob/master/src/main/resources/gui.png)
