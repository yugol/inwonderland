/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc.toolbox;

import java.util.List;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Tool {

    public static final String CELL_SEP = "<:>";
    public static final String HEAD_SEP = "<~>";
    private final String[] names;
    private final String[] args;
    private final String[] argDesc;
    private final String[] desc;
    private final String[] example;

    Tool(List<String> descnames, List<String> arguments, List<String> argDescs, List<String> docPars, List<String> smpCode) {
        names = new String[descnames.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = descnames.get(i);
        }

        args = new String[arguments.size()];
        for (int i = 0; i < args.length; i++) {
            args[i] = arguments.get(i);
        }

        argDesc = new String[argDescs.size()];
        for (int i = 0; i < argDesc.length; i++) {
            argDesc[i] = argDescs.get(i);
        }

        desc = new String[docPars.size()];
        for (int i = 0; i < desc.length; i++) {
            desc[i] = docPars.get(i);
        }

        example = new String[smpCode.size()];
        for (int i = 0; i < example.length; i++) {
            example[i] = smpCode.get(i);

        }
    }

    public String getName() {
        return names[0];
    }

    public String getSnippet() {
        StringBuilder sb = new StringBuilder(getName());
        sb.append("(");
        if (args.length > 0) {
            int argCnt = 0;
            for (String arg : args) {
                if (arg.indexOf("=") < 0) {
                    if (argCnt > 0) {
                        sb.append(", ");
                    }
                    sb.append(arg);
                    ++argCnt;
                }
            }
        }
        sb.append(")\n");
        return sb.toString();
    }

    public String toHTMLString() {
        StringBuilder sb = new StringBuilder("<html><body bgcolor='#FFFFFF'>");
        sb.append("<table width='640'><tr><td>");

        for (String name : names) {
            sb.append("<b>" + name + "</b>");
            sb.append("(");
            if (args.length > 0) {
                sb.append(" ");
                String arg = args[0];
                appendHTMLArgument(sb, arg, true);
                for (int i = 1; i < args.length; i++) {
                    arg = args[i];
                    sb.append(", ");
                    appendHTMLArgument(sb, arg, true);
                }
                sb.append(" ");
            }
            sb.append(")");
            sb.append("<br/>");
        }

        if (args.length > 0 && argDesc.length > 0) {
            sb.append("<table>");
            sb.append("<tr>");
            sb.append("<td>");
            if (args.length > 1) {
                sb.append("<u>Arguments:</u>");
            } else {
                sb.append("<u>Argument:</u>");
            }
            sb.append("</td>");
            // sb.append("<td>&nbsp;</td>");
            sb.append("<td>");
            sb.append("<ul>");
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                sb.append("<li>");
                appendHTMLArgument(sb, arg, false);
                sb.append(" ");
                sb.append(argDesc[i]);
                sb.append("</li>");
            }
            sb.append("</ul>");
            sb.append("</td>");
            sb.append("</tr>");
            sb.append("</table>");
        } else {
            sb.append("<br/>");
        }

        int pars = 0;
        for (int i = 0; i < desc.length; i++) {
            String par = desc[i];
            if (par.indexOf(HEAD_SEP) >= 0) {
                String[] table = par.split(HEAD_SEP);
                String[] heads = table[0].split(CELL_SEP);
                String[] cells = table[1].split(CELL_SEP);
                sb.append("<table border='0' align='center'>");
                sb.append("<tr>");
                for (String head : heads) {
                    sb.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;");
                    sb.append(head);
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;</th>");
                }
                sb.append("</tr>");
                for (int j = 0; j < cells.length; j += heads.length) {
                    sb.append("<tr>");
                    for (int k = 0; k < heads.length; k++) {
                        String cell = cells[j + k];
                        sb.append("<td align='center'>");
                        sb.append(cell);
                        sb.append("</td>");
                    }
                    sb.append("</tr>");
                }
                sb.append("</table>");
                pars = 0;
            } else if (par.indexOf(CELL_SEP) >= 0) {
                String[] items = par.split(CELL_SEP);
                sb.append("<ul>");
                for (String item : items) {
                    sb.append("<li>");
                    sb.append(item);
                    sb.append("</li>");
                }
                sb.append("</ul>");
                pars = 0;
            } else {
                if (par.indexOf("<tt>") == 0) {
                    sb.append("<br/><p>");
                    sb.append(par);
                    sb.append("</p>");
                } else {
                    sb.append("<p>");
                    if (pars > 0) {
                        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                    }
                    sb.append(par);
                    sb.append("</p>");
                }
                ++pars;
            }
        }

        if (example.length > 0) {
            sb.append("<table><tr><td><dl><dt><u>Example:</u></dt></dl></td></tr></table>");
            sb.append("<tt>");
            for (String line : example) {
                if (line.indexOf(">>>") == 0) {
                    line = line.substring(4);
                    sb.append("<font color='#CC0000'>&gt;&gt;&gt;</font>");
                    sb.append(line);
                } else if (line.indexOf("...") == 0) {
                    line = line.substring(4);
                    sb.append("<font color='#CC0000'>...&nbsp;&nbsp;&nbsp;&nbsp;</font>");
                    sb.append(line);
                } else {
                    sb.append(line);
                }

                sb.append("<br/>");
            }
            sb.append("</tt>");
        }

        sb.append("</td></tr></table>");
        sb.append("</body></html>");
        return sb.toString();
    }

    private void appendHTMLArgument(StringBuilder sb, String arg, boolean addImplicit) {
        String[] chunks = arg.split("=");
        sb.append("<em>" + chunks[0] + "</em>");
        if (addImplicit && chunks.length > 1) {
            sb.append("=");
            sb.append("<tt>" + chunks[1] + "</tt>");
        }
    }
}
