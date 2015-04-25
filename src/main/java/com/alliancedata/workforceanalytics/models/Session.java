package com.alliancedata.workforceanalytics.models;

import javafx.beans.property.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of a user Session.
 * Sessions allow users to use one or more data files to generate reports.
 * Each session references a database populated with data from the user's specified data files.
 */
public class Session implements Serializable
{
    // region Fields
    private final IntegerProperty id;
    private final ObjectProperty<Date> date;
    private final ObjectProperty<DatabaseHandler> databaseHandler;
	// endregion

    // region Constructors
    /**
     * Creates a new {@code Session} object with the specified parameters.
     * @param id The unique ID number of the Session
     * @param databaseHandler The {@code DatabaseHandler} object used for database interactions
     */
    public Session(Integer id, DatabaseHandler databaseHandler)
    {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleObjectProperty<>(Calendar.getInstance().getTime());
        this.databaseHandler = new SimpleObjectProperty<>(databaseHandler);
    }
	// endregion

    // region Properties
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

	@NotNull
	/**
	 * Gets the Session's date property
	 */
	public ObjectProperty<Date> dateProperty()
	{
		return this.date;
	}
	// endregion

    // region Methods
    /**
     * Gets the Session's id.
     */
    @NotNull
    public Integer getId()
    {
        return this.id.getValue();
    }

    /**
     * Gets the Session's database handler.
     */
    @NotNull
    public DatabaseHandler getDatabaseHandler()
    {
        return this.databaseHandler.getValue();
    }

	/**
	 * Gets the Session's date.
	 */
	@NotNull
	public Date getDate()
	{
		return this.date.getValue();
	}

    /**
     * Sets the database handler of the session.
     * @param databaseHandler The new database handler for the session
     */
    public void setDatabaseHandler(@NotNull DatabaseHandler databaseHandler)
    {
        this.databaseHandler.setValue(databaseHandler);
    }
	// endregion
}
