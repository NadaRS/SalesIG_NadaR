package Controller;

import org.example.View;
import Model.InvoiceHeader;
import Model.InvoiceLine;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controller implements ActionListener , MouseListener, ListSelectionListener {

    View view;
    public Controller() {}

    public Controller(View view) {
        this.view = view;
    }

    //addMouseListener(new MouseListen())
    private static String status;
    //private JTable headerJTable;
    //private DefaultTableModel model;

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        View getView = new View();
        getView.getInvItemsTbl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable table = (JTable) e.getSource();
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1){
                    Controller c = new Controller();
                    c.getDataOfLineTabel();
                }
            }
        });
    }

    public void mousePressed(MouseEvent e) {
        View getView = new View();
        getView.getInvItemsTbl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JTable table = (JTable) e.getSource();
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1){
                    Controller c = new Controller();
                    c.getDataOfLineTabel();
                }
            }
        });
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


//    public void ConvertArrayListToStringArray() throws IOException {
//        ArrayList<InvoiceHeader> arrayList = new ArrayList<>();
//        InvoiceHeader invoiceHeader = new InvoiceHeader();
//        View view1 = new View();
//        arrayList = invoiceHeader.readFile();
//
//        String[][] invoiceHData = new String[arrayList.size()][4];
//
//        for (int x = 0; x < arrayList.size(); x++) {
//            InvoiceHeader data = arrayList.get(x);
//
//            invoiceHData[x][0] = String.valueOf(data.getInvoiceNum());
//            invoiceHData[x][1] = data.getInvoiceDate().toString();
//            invoiceHData[x][2] = data.getCustomerName();
//            invoiceHData[x][3] = String.valueOf(data.getTotal());
//            System.out.println(invoiceHData[x][0]+" "+invoiceHData[x][1]+" "+invoiceHData[x][2]+" "+invoiceHData[x][3]+" ");
//        }
//        view1.setDataInvTbl(invoiceHData);
//    }
//    protected void fillInvoiceTabel() {
//
//    }

    protected void getDataOfInvoiceTabel() throws IOException {
        ArrayList<InvoiceHeader> arrayList = new ArrayList<>();
        InvoiceHeader invoiceHeader = new InvoiceHeader();
        arrayList = invoiceHeader.readFile();
        DefaultTableModel model = (DefaultTableModel)view.getInvoiceTbl().getModel();

        Object[] objects;
        for (int x = 0; x < arrayList.size(); x++) {
            objects = new Object[4];
            objects[0] = arrayList.get(x).getInvoiceNum();
            objects[1] = arrayList.get(x).getInvoiceDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            objects[2] = arrayList.get(x).getCustomerName();
            objects[3] = arrayList.get(x).getTotal();
            model.addRow(objects);
        }
    }

    public void getDataOfLineTabel(){
        ArrayList<InvoiceLine> arrayList = new ArrayList<>();
        InvoiceLine invoiceLine = new InvoiceLine();
        arrayList = invoiceLine.readFile();
        DefaultTableModel model = (DefaultTableModel)view.getInvItemsTbl().getModel();

        Object[] objects;
        for(int x=0; x<arrayList.size(); x++) {
            objects = new Object[5];
            objects[0] = arrayList.get(x).getInvoiceNum();
            objects[1] = arrayList.get(x).getItemName();
            objects[2] = arrayList.get(x).getItemPrice();
            objects[3] = arrayList.get(x).getCount();
            objects[4] = arrayList.get(x).getItemTotal();
            model.addRow(objects);
        }
    }


//    protected void deleteHeaderSelectedRow(){
//        DefaultTableModel tableModel = (DefaultTableModel) this.headerJTable.getModel();
//        int[] selectedRows = headerJTable.getSelectedRows();
//        if(selectedRows.length > 0){
//            for (int i=0; i< selectedRows.length; i++){
//                tableModel.removeRow(selectedRows[i]);
//            }
//        }
//    }

    public static String getStatus() {
        return status;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
       switch (actionEvent.getActionCommand()){
           case "loadFile" :
               try {
                   getDataOfInvoiceTabel();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               status = "Loaded";
               getDataOfLineTabel();
               break;

           case "saveFile" :
               status = "Saved";
               break;

           case "createNewInvoiceBtn" :
               status = "Created";
               break;

           case "customerNameTF" :
               break;

           case "deleteInvoice_btn" :
               //deleteHeaderSelectedRow();
               status = "Deleted";
               break;

           case "save_btn" :
               status = "Saved";
               break;

           case "cancel_btn" :
               status = "Canceled";
               break;

        }
    }
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        System.out.println("t");
        View getView = new View();
        getView.getInvItemsTbl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JTable table = (JTable) e.getSource();
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1){
                    getDataOfLineTabel();
                }
            }
        });
    }
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {

        View getView = new View();
        System.out.println("t");
        getView.getInvItemsTbl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JTable table = (JTable) e.getSource();
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1){
                    getDataOfLineTabel();
                }
            }
        });
    }
}

