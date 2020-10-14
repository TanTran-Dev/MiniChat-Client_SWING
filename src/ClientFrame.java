import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ClientFrame extends JFrame implements Runnable {
    // Variables declaration - do not modify
    private JButton btnSave;
    private JButton btnSend;
    private JTextField edtIPAddressReceiver;
    private JTextField edtInputMessage;
    private JTextField edtPort;
    private JTextField edtSenderName;
    private JLabel labelIPAddressReceiver;
    private JLabel labelSenderName;
    private JLabel labelPort;
    private JScrollPane jScrollPane1;
    private JList<String> listMessage;
    private JPanel panelChat;
    private JPanel panelConfig;
    // End of variables declaration
    private List<String> messages;
    DatagramSocket clientSocket;
    InetAddress IPAddress;

    String requestToServer = "";
    String response = "";

    private Thread thread;
    private boolean isRunning;
    private boolean isConnected;
    private DefaultListModel<String> listModel;

    public ClientFrame() {
        setTitle("Mini Chat (Client)");
        initComponents();
    }

    private void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        panelConfig = new JPanel();
        labelIPAddressReceiver = new JLabel();
        edtIPAddressReceiver = new JTextField();
        labelSenderName = new JLabel();
        edtSenderName = new JTextField();
        labelPort = new JLabel();
        edtPort = new JTextField();
        btnSave = new JButton();
        panelChat = new JPanel();
        jScrollPane1 = new JScrollPane();
        listMessage = new JList<>();
        edtInputMessage = new JTextField();
        btnSend = new JButton();

        messages = new ArrayList<>();


//        Sender sender = new Sender("Tan", "Hello");
//
//        senderList.add(sender);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panelConfig.setBorder(BorderFactory.createTitledBorder(null, "Cấu hình"));

        labelIPAddressReceiver.setText("IP Người nhận:");


        labelSenderName.setText("Tên người gửi:");


        labelPort.setText("Cổng:");

        edtPort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                edtPortActionPerformed(evt);
            }
        });

        btnSave.setText("Kết nối");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnSaveActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSend.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSendActionPerformed(e);
            }
        });

        GroupLayout panelConfigLayout = new GroupLayout(panelConfig);
        panelConfig.setLayout(panelConfigLayout);
        panelConfigLayout.setHorizontalGroup(
                panelConfigLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelConfigLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(panelConfigLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelIPAddressReceiver)
                                        .addComponent(labelSenderName))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelConfigLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(edtIPAddressReceiver, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                        .addComponent(edtSenderName))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelPort)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edtPort, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        panelConfigLayout.setVerticalGroup(
                panelConfigLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelConfigLayout.createSequentialGroup()
                                .addGroup(panelConfigLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(panelConfigLayout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addGroup(panelConfigLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelIPAddressReceiver)
                                                        .addComponent(edtIPAddressReceiver, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(panelConfigLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(edtSenderName, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(labelSenderName)))
                                        .addGroup(panelConfigLayout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addGroup(panelConfigLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(labelPort)
                                                        .addComponent(edtPort, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(21, Short.MAX_VALUE))
        );

        panelChat.setBorder(BorderFactory.createTitledBorder(null, "Tin nhắn"));

        listModel = new DefaultListModel<String>() {
            @Override
            public int getSize() {
                return messages.size();
            }

            @Override
            public String getElementAt(int index) {
                return messages.get(index);
            }
        };

        listMessage.setModel(listModel);
        jScrollPane1.setViewportView(listMessage);

        btnSend.setText("Gửi");

        GroupLayout panelChatLayout = new GroupLayout(panelChat);
        panelChat.setLayout(panelChatLayout);
        panelChatLayout.setHorizontalGroup(
                panelChatLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelChatLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1)
                                .addContainerGap())
                        .addGroup(panelChatLayout.createSequentialGroup()
                                .addComponent(edtInputMessage, GroupLayout.PREFERRED_SIZE, 489, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSend, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addGap(12, 12, 12))
        );
        panelChatLayout.setVerticalGroup(
                panelChatLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelChatLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelChatLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(edtInputMessage, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(panelConfig, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(panelChat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(panelConfig, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panelChat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void edtPortActionPerformed(ActionEvent event) {
        // TODO add your handling code here:
    }

    private void btnSaveActionPerformed(ActionEvent event) throws IOException {
        if (!edtIPAddressReceiver.getText().isEmpty()) {
            String ipAddress = edtIPAddressReceiver.getText().trim();
            if (!edtPort.getText().isEmpty()) {
                int port = Integer.parseInt(edtPort.getText().trim());
                connectToServer(ipAddress, port);
            } else {
                JOptionPane.showMessageDialog(null, "Port không được để trống");
            }
        } else {
            JOptionPane.showMessageDialog(null, "IP không được để trống");
        }
    }

    private void btnSendActionPerformed(ActionEvent event) {
        if (isConnected) {
            if (!edtIPAddressReceiver.getText().isEmpty()) {
                if (!edtPort.getText().isEmpty()) {
                    sendData();
                    edtInputMessage.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Nhập PORT!!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nhập IP!!!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Thiết bị chưa được kết nối, không thể nhắn tin.");
        }
    }

    private void connectToServer(String ipAddress, Integer port) throws IOException {
        clientSocket = new DatagramSocket();

        if (ipAddress != null) {
            if (port != null) {
                IPAddress = InetAddress.getByName(ipAddress);
                startThread();
                JOptionPane.showMessageDialog(null, "Kết nối tới địa chỉ: " + IPAddress + " thành công");
                isConnected = true;
            } else {
                JOptionPane.showMessageDialog(null, "Kết nối không thành công do thiếu Port");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kết nối không thành công do thiếu IP máy chủ");
        }
    }

    private void sendData() {
        byte[] sendData;

        String senderName = IPAddress.getHostName();
        if (edtSenderName.getText().isEmpty()) {
            requestToServer = senderName + ": " + edtInputMessage.getText().trim();
        } else {
            requestToServer = edtSenderName.getText() + ": " + edtInputMessage.getText().trim();
        }
        listModel.addElement(requestToServer);
        messages.add(requestToServer);

        sendData = requestToServer.getBytes();

        int port = Integer.parseInt(edtPort.getText().trim());
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        try {
            clientSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveData() {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            clientSocket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String modifiedSentence = new String(receivePacket.getData()).trim();
        listModel.addElement(modifiedSentence);
        messages.add(modifiedSentence);
        response = modifiedSentence;
    }

    @Override
    public void run() {
        while (!isRunning) {
            receiveData();
        }

        clientSocket.close();
    }
}
