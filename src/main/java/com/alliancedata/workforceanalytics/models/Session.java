package com.alliancedata.workforceanalytics.models;

import javafx.beans.property.*;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of a user session.
 * Sessions allow users to use one or more data files to generate reports.
 * Each session has a database populated with data from the files the user selected.
 */
public class Session
{
    // Fields:
    private final StringProperty name;
    private final IntegerProperty id;
    private final ObjectProperty<DatabaseHandler> databaseHandler;

    // Constructors:
    public Session(@NotNull String name, @NotNull Integer id, @NotNull DatabaseHandler handler)
    {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleIntegerProperty(id);
        this.databaseHandler = new SimpleObjectProperty<>(handler);
    }

    // Properties:
    @NotNull
    public StringProperty nameProperty()
    {
        return this.name;
    }

    @NotNull
    public IntegerProperty idProperty()
    {
        return this.id;
    }

    @NotNull
    public ObjectProperty<DatabaseHandler> databaseHandlerProperty()
    {
        return this.databaseHandler;
    }

    // Functions:
    @NotNull
    public String getName()
    {
        return this.name.getValue();
    }

    @NotNull
    public Integer getId()
    {
        return this.id.getValue();
    }

    @NotNull
    public DatabaseHandler getDatabaseHandler()
    {
        return this.databaseHandler.getValue();
    }

    public void setName(@NotNull String name)
    {
        this.name.setValue(name);
    }

    public void setId(@NotNull Integer id)
    {
        // TODO: ensure unique session IDs across all sessions
        this.id.setValue(id);
    }

    public void setDatabaseHandler(@NotNull DatabaseHandler databaseHandler)
    {
        this.databaseHandler.setValue(databaseHandler);
    }
}
