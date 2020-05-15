# Project stats
Count source lines of code
## Options

* `--cli` *Run without GUI*

* `--gui` *Run without CLI*

* `--wait` *Run without progressbar*

* `--list` *List files*

* `--list-skipped` *List skipped files and dirs*

* `--list-ext=ext` *List files with given extension*

* `--check-dir=dir` *Check if directory is skipped*

* `--check-file=file` *Check if file is skipped (absolute path)*

* `--skip-dir=dir` *Skip directory*

* `--skip-file=file` *Skip file (absolute path)*

* `--skip-ext=ext` *Skip files with given extension*

* `--no-ext` *Don't show extensions*

* `--help` *Print help*

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
size: 21,89 KiB / 22,42 KB
files: 14
folders: 9
lines total: 670
empty lines: 101
not empty lines: 569
skipped files: 7
extensions: 
  java (7)
  [none] (1)
  gitignore (1)
  xml (1)
  md (1)
  yml (1)
  mf (1)
  iml (1)
```
