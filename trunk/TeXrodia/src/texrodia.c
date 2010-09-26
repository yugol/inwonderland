/*
 * to use it with texmaker
 * 1) copy texrodia to /usr/bin
 * 2) create TeX files with extension .rodia
 * 3) use the following quick command:
 *    texrodia %.rotex %.tex|pdflatex -interaction=nonstopmode %.tex|evince %.pdf
 */


#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[])
{
    FILE* in = stdin;
    FILE* out = stdout;

    if (argc > 1) {
        in = fopen(argv[1], "r");
        if (in == NULL) {
            fprintf(stderr, "Could not open input file\n");
            return EXIT_FAILURE;
        }
        if (argc > 2) {
            out = fopen(argv[2], "w");
            if (out == NULL) {
                fprintf(stderr, "Could not open output file\n");
                return EXIT_FAILURE;
            }
        }
    }

    while (1) {
        int c0 = fgetc(in);

        if (c0 == EOF) {
            break;
        }

        if (c0 == 0xC3) {
            int c1 = fgetc(in);
            switch (c1) {
                case 0x82:
                    fputs("\\^{A}", out);
                    break;
                case 0x8E:
                    fputs("\\^{I}", out);
                    break;
                case 0xA2:
                    fputs("\\^{a}", out);
                    break;
                case 0xAE:
                    fputs("\\^{i}", out);
                    break;
                default:
                    fputc(c0, out);
                    fputc(c1, out);
            }
            continue;
        }

        if (c0 == 0xC4) {
            int c1 = fgetc(in);
            switch (c1) {
                case 0x82:
                    fputs("\\u{A}", out);
                    break;
                case 0x83:
                    fputs("\\u{a}", out);
                    break;
                default:
                    fputc(c0, out);
                    fputc(c1, out);
            }
            continue;
        }

        if (c0 == 0xC8) {
            int c1 = fgetc(in);
            switch (c1) {
                case 0x98:
                    fputs("\\c{S}", out);
                    break;
                case 0x99:
                    fputs("\\c{s}", out);
                    break;
                case 0x9A:
                    fputs("\\c{T}", out);
                    break;
                case 0x9B:
                    fputs("\\c{t}", out);
                    break;
                default:
                    fputc(c0, out);
                    fputc(c1, out);
            }
            continue;
        }

        fputc(c0, out);
    }

    fclose(in);
    fclose(out);

    return EXIT_SUCCESS;
}
