package com.alliancedata.workforceanalytics.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import java.io.File;

/**
 *  Implementation of a database handler.
 *  The database handler is a per-{@code Session} object which
 *  handles database reads and query executions.
 */
public class DatabaseHandler
{
    // Fields:
    private final ObjectProperty<File> databaseFile;

    // Constructors:
    public DatabaseHandler(@NotNull File databaseFile)
    {
        this.databaseFile = new SimpleObjectProperty<>(databaseFile);
    }

    public DatabaseHandler(@NotNull String fileName)
    {
        this.databaseFile = new SimpleObjectProperty<>(new File(fileName));
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

    // TODO: fetch(), execute(String query), getData()
}
