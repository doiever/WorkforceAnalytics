package com.alliancedata.workforceanalytics.models;

import javafx.beans.property.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Implementation of a user Session.
 * Sessions allow users to use one or more data files to generate reports.
 * Each session references a database populated with data from the user's specified data files.
 */
public class Session implements Serializable
{
    // Fields:
    private final StringProperty name;
    private final IntegerProperty id;
    private final ObjectProperty<DatabaseHandler> databaseHandler;

    // Constructors:
    /**
     * Creates a new {@code Session} object with the specified parameters.
     * @param name The name of the Session
     * @param id The unique ID number of the Session
     * @param databaseHandler The {@code DatabaseHandler} object used for database interactions
     */
    public Session(String name, Integer id, DatabaseHandler databaseHandler)
    {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleIntegerProperty(id);
        this.databaseHandler = new SimpleObjectProperty<>(databaseHandler);
    }

    // Properties:
    @NotNull
    /**
     * Gets the Session's name property.
     */
    public StringProperty nameProperty()
    {
        return this.name;
    }

    @NotNull
    /**
     * Gets the Session's id property.
     */
    public IntegerProperty idProperty()
    {
        return this.id;
    }

    @NotNull
    /**
     * Gets the Session's database handler property.
     */
    public ObjectProperty<DatabaseHandler> databaseHandlerProperty()
    {
        return this.databaseHandler;
    }

    // Functions:
    @NotNull
    /**
     * Gets the Session's name.
     */
    public String getName()
    {
        return this.name.getValue();
    }

    @NotNull
    /**
     * Gets the Session's id property.
     */
    public Integer getId()
    {
        return this.id.getValue();
    }

    @NotNull
    /**
     * Gets the Session's database handler.
     */
    public DatabaseHandler getDatabaseHandler()
    {
        return this.databaseHandler.getValue();
    }

    /**
     * Sets the name of the Session.
     * @param name The new name of the session
     */
    public void setName(@NotNull String name)
    {
        this.name.setValue(name);
    }

    /**
     * Sets the id of the Session.
     * @param id The new id of the session
     */
    private void setId(@NotNull Integer id)
    {
        this.id.setValue(id);
    }

    /**
     * Sets the database handler of the session.
     * @param databaseHandler The new database handler for the session
     */
    public void setDatabaseHandler(@NotNull DatabaseHandler databaseHandler)
    {
        this.databaseHandler.setValue(databaseHandler);
    }
}
