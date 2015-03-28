# TeX.ro.dia #
A tiny filter used to alter `.tex` files before processing
them with (La)TeX. It changes Romanian diacritics with corresponding TeX
macros as can be seen in the table below.

| **Unicode character** | **TeX macro** |
|:----------------------|:--------------|
| Ă | \u{A} |
| ă | \u{a} |
| Â | \^{A} |
| â | \^{a} |
| Î | \^{I} |
| î | \^{i} |
| Ș | \c{S} |
| ș | \c{s} |
| Ț | \c{T} |
| ț | \c{t} |

**Why**
  * source easier to write
  * source easier to read (by humans)
  * can use spell-checker

## Usage ##

The filter is intended to be used with [Texmaker](http://www.xm1math.net/texmaker/):

  1. copy `texrodia` to a visible path ( e.g. `/usr/bin` )
  1. **!!** create TeX files with extension `.rodia`
  1. use the following quick command (or something like):
> > `texrodia %.rotex %.tex|pdflatex -interaction=nonstopmode %.tex|evince %.pdf`