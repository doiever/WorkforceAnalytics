package com.alliancedata.workforceanalytics.models;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteJob;
import com.almworks.sqlite4java.SQLiteQueue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 *  Implementation of a Database Handler.
 *  The Database Handler is a per-Session object which handles database interaction.
 */
public class DatabaseHandler implements Serializable
{
    // Fields:
    transient private SQLiteConnection databaseConnection;
    private final ObjectProperty<File> databaseFile;

    // Constructors:
    public DatabaseHandler(@NotNull String fileName)
    {
        this.databaseFile = new SimpleObjectProperty<>();
        File file = tryCreateFile(fileName);

        if (file != null && file.exists())
        {
            databaseFile.setValue(file);
        }
        else
        {
            // TODO
        }
    }

    // Properties:
    @NotNull
    public ObjectProperty<File> databaseFileProperty()
    {
        return this.databaseFile;
    }

    // Methods:
    @NotNull
    public File getDatabaseFile()
    {
        return this.databaseFile.getValue();
    }

    public void setDatabaseFile(@NotNull File databaseFile)
    {
        this.databaseFile.setValue(databaseFile);
    }

    public void setFile(@NotNull String fileName)
    {
        this.databaseFile.setValue(new File(fileName));
    }

    public boolean executeQuery(@NotNull String query)
    {
        Boolean success = null;
        SQLiteQueue queue = new SQLiteQueue(this.getDatabaseFile());

        try
        {
            queue.start();

            SQLiteJob<Boolean> queryJob = new SQLiteJob<Boolean>() {
                @Override
                protected Boolean job(SQLiteConnection connection) throws Throwable {
                    try
                    {
                        connection.exec(query);
                    }
                    catch (SQLiteException ex)
                    {
                        // TODO
                        ex.printStackTrace();
                        return false;
                    }

                    return true;
                }
            };

            success = queue.execute(queryJob).complete();
        }
        catch (IllegalStateException ex)
        {
            // TODO
            ex.printStackTrace();
            success = false;
        }
        finally
        {
            try
            {
                queue.stop(true).join();
            }
            catch (InterruptedException ex)
            {
                // TODO
                ex.printStackTrace();
            }
            finally
            {
                queue.stop(false);
            }
        }

        return success;
    }

    /**
     * Attempts to create the specified file.
     * @param fileName The full path of the file to create.
     * @return An {@code File} object at the specified location.
     */
    private static File tryCreateFile(@NotNull String fileName)
    {
        boolean fileCreated = false;

        try
        {
            File file = new File(fileName);
            fileCreated = file.createNewFile();
            return file;
        }
        catch (IOException ex)
        {
            // TODO
            ex.printStackTrace();
            return null;
        }
        catch (SecurityException ex)
        {
            // TODO
            ex.printStackTrace();
            return null;
        }
    }
}
