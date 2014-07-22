package aibroker.agents.monitor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class SequenceSelectionDialog extends JDialog {

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        try {
            final SequenceSelectionDialog dialog = new SequenceSelectionDialog();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private final JPanel  contentPanel = new JPanel();
    private JList<String> lstSequences;
    private String        selectedName;
    private JButton       okButton;
    private JScrollPane   scrollPane;

    /**
     * Create the dialog.
     */
    public SequenceSelectionDialog() {
        setType(Type.UTILITY);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setModal(true);
        setAlwaysOnTop(true);
        setBounds(100, 100, 250, 480);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            scrollPane = new JScrollPane();
            contentPanel.add(scrollPane, BorderLayout.CENTER);
            lstSequences = new JList<String>();
            scrollPane.setViewportView(lstSequences);
            lstSequences.addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(final ListSelectionEvent e) {
                    okButton.setEnabled(true);
                }

            });
            lstSequences.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(final MouseEvent evt) {
                    if (evt.getClickCount() >= 2) {
                        selectedName = lstSequences.getSelectedValue();
                        dispose();
                    }
                }

            });

            lstSequences.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        {
            final JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        selectedName = lstSequences.getSelectedValue();
                        dispose();
                    }
                });
                okButton.setEnabled(false);
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                final JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        dispose();
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

    public String getSelectedName() {
        return selectedName;
    }

    public void setValues(final String... values) {
        lstSequences.setModel(new AbstractListModel<String>() {

            @Override
            public String getElementAt(final int index) {
                return values[index];
            }

            @Override
            public int getSize() {
                return values.length;
            }

        });
    }

}
