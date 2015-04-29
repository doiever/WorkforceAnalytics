package com.alliancedata.workforceanalytics.models;

import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Implementation of a user Session.
 * Sessions allow users to use one or more data files to generate reports.
 * Each session references a database populated with data from the user's specified data files.
 */
public class Session implements Serializable
{
	// region Fields
	private int id = -1;
	private Date date = new Date();
	private DatabaseHandler databaseHandler = null;
	// endregion

	// region Constructors
	/**
	 * Creates a new {@code Session} object with the specified parameters.
	 * @param id The unique ID number of the Session
	 * @param databaseHandler The {@code DatabaseHandler} object used for database interactions
	 */
	public Session(Integer id, DatabaseHandler databaseHandler)
	{
		this.id = id;
		this.databaseHandler = databaseHandler;
	}
	// endregion

	// region Methods
	/**
	 * Gets the Session's id.
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * Gets the Session's date.
	 */
	@NotNull
	public Date getDate()
	{
		return this.date;
	}

	/**
	 * Gets the Session's database handler.
	 */
	@NotNull
	public DatabaseHandler getDatabaseHandler()
	{
		return this.databaseHandler;
	}
	// endregion
}
