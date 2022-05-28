package Model;

import com.opencsv.CSVWriter;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InvoiceHeader {
    private int invoiceNum = 0;
    private LocalDate invoiceDate;
    private String customerName;
    private double total;
    private ArrayList<InvoiceHeader> aL;

    private String[] headerInvTbl = {"No.", "Date", "Customer", "Total"};

    public InvoiceHeader() {}

    public InvoiceHeader(int invoiceNum, LocalDate invoiceDate, String customerName, double total) {
        this.invoiceNum = invoiceNum;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
        this.total = total;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }
    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate =  invoiceDate;
                //LocalDate.now();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) throws IOException {
        if(total == 0){
            this.total = calculateTotalCost();
        }
        else {
            this.total = total;
        }
    }
    private double calculateTotalCost() throws IOException {
        double totalCost=0;
        ArrayList<InvoiceHeader> arrayList = new InvoiceHeader().readFile();
        ArrayList<InvoiceLine> arrayListLine = new InvoiceLine().readFile();
        for (int i=0; i<arrayList.size() ; i++){
            for (int j=0; j<arrayListLine.size() ; j++) {
                if (arrayList.get(i).getInvoiceNum() == arrayListLine.get(j).getInvoiceNum()) {
                    totalCost += arrayListLine.get(j).getItemTotal();
                }
            }
            //System.out.println("total cost of invoice"+totalCost);
        }
        return totalCost;
    }
    public String[] getHeaderInvTbl() {
        return headerInvTbl;
    }

    //Read Invoice Header
    public ArrayList<InvoiceHeader> readFile() throws IOException {
        FileReader fileReader = new FileReader(new File("InvoiceHeader.csv"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<InvoiceHeader> arrayList;
        arrayList = readData(bufferedReader);
        bufferedReader.close();
        return arrayList;
    }
    public ArrayList<InvoiceHeader> readData(BufferedReader bufferedReader) throws IOException {
        ArrayList<InvoiceHeader> arraylist = new ArrayList<>();
        while (bufferedReader.ready()) {
            arraylist.add(readInvoice(bufferedReader));
        }
        return arraylist;
    }

    public InvoiceHeader readInvoice (BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        int invoiceNum = Integer.parseInt(line.split(",")[0]);
        LocalDate date = LocalDate.parse(line.split(",")[1], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String customerName = line.split(",")[2];
        double total = Double.parseDouble(line.split(",")[3]);

        return new InvoiceHeader(invoiceNum , date, customerName, total);
    }
    //Write Invoice Header
    void writeFile(ArrayList<InvoiceHeader> a) {
        try{
            String csv = "InvoiceHeader.csv";
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csv , true), ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "");

            int invoiceNumber= a.get(0).getInvoiceNum();
            LocalDate date = a.get(0).getInvoiceDate();
            String customerName = a.get(0).getCustomerName();
            double total = a.get(0).getTotal();
            setTotal(total);
            total = getTotal();
            String[] dataToWrite = {String.valueOf(invoiceNumber), date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), customerName, String.valueOf(total)};
            String[] newLine = {"\n"};
            csvWriter.writeNext(newLine, false);
            csvWriter.writeNext(dataToWrite, false);
            csvWriter.flush();
            csvWriter.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
