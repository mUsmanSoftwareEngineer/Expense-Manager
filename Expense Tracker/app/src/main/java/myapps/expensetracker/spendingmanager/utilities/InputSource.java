package myapps.expensetracker.spendingmanager.utilities;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface InputSource {
    InputStream open() throws FileNotFoundException;

    long length();
}
