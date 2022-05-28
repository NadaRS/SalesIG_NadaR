package Model;

import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.util.ArrayList;

public class InvoiceLine {
    private int invoiceNum;
    private String itemName;
    private double itemPrice;
    private int count; //number of items purchased
    private double itemTotal;

    public InvoiceLine() {
    }

    public InvoiceLine(int invoiceNum, String itemName, double itemPrice, int count, double itemTotal) {
        this.invoiceNum = invoiceNum;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.itemTotal = itemTotal;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(double itemTotal) {
        if(itemTotal == 0){
            calculateItemTotal();
        }
        else{
            this.itemTotal = itemTotal;
        }
    }
    private double calculateItemTotal(){
        return this.itemTotal= this.itemPrice * this.count;
    }
    //Read Line File
    public ArrayList<InvoiceLine> readFile() {
        ArrayList<InvoiceLine> arrayList=null;
        try {
            FileReader fileReader = new FileReader(new File("InvoiceLine.csv"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            arrayList = readData(bufferedReader);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        }catch (IOException e) {
            System.out.println("FileNotFoundException");
        }
        return arrayList;
    }
    public ArrayList<InvoiceLine> readData(BufferedReader bufferedReader) throws IOException {
        ArrayList<InvoiceLine> arraylist = new ArrayList<>();
        while (bufferedReader.ready()) {
            arraylist.add(readInvoice(bufferedReader));
        }
        return arraylist;
    }

    public InvoiceLine readInvoice (BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        int invoiceNum = Integer.parseInt(line.split(",")[0]);
        String itemName = line.split(",")[1];
        double itemPrice = Double.parseDouble(line.split(",")[2]);
        int count = Integer.parseInt(line.split(",")[3]);
        double itemTotal = Double.parseDouble(line.split(",")[4]);;

        return new InvoiceLine(invoiceNum , itemName, itemPrice, count, itemTotal);
    }
    //Write Invoice Line
    void writeFile(ArrayList<InvoiceLine> a) {
        try{
            String csv = "InvoiceLine.csv";
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csv , true), ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "");
            int invoiceNumber= a.get(0).getInvoiceNum();
            String itemName = a.get(0).getItemName();
            double itemPrice = a.get(0).getItemPrice();
            int count = a.get(0).getCount();
            double itemTotal = a.get(0).getItemTotal();
            String[] dataToWrite = {String.valueOf(invoiceNumber), itemName, String.valueOf(itemPrice), String.valueOf(count), String.valueOf(itemTotal)};
            String[] newLine = {"\n"};
            csvWriter.writeNext(newLine, false);
            csvWriter.writeNext(dataToWrite, false);
            csvWriter.flush();
            csvWriter.close();
        }
        catch (FileNotFoundException e){
            System.out.println("FileNotFoundException");
        }
        catch (DirectoryNotEmptyException e){
            System.out.println("DirectoryNotFoundException");
        }
        catch (IOException e){
            System.out.println("IOException -- RuntimeException --");
        }
    }
}
