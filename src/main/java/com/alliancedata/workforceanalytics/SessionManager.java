package com.alliancedata.workforceanalytics;

import com.alliancedata.workforceanalytics.models.DatabaseHandler;
import com.alliancedata.workforceanalytics.models.Session;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Paths;

/**
 *  Singleton implementation of the system's Session Manager.
 *  The session manager remembers the user's current and previous Session.
 *  It also allows for the creation and selection of {@code Session} objects
 *  and maintains the latest session ID (similar to auto-incrementing database columns).
 */
public final class SessionManager implements Serializable
{
	// region Fields
	private static transient SessionManager instance = null;
	private Session previousSession = null;
	private Session currentSession = null;
	private Integer latestSessionId = 0;

	/**
	 * Version of the {@code SessionManager} class, used for serialization/deserialization validation.
	 * Don't change this unless you're making structural changes to a serializable class with the intent
	 * of changing serializable/transient fields, as version mismatches between this field and any serialized
	 * {@code SessionManager} objects will throw an {@code InvalidClassException}.<br><br>See the documentation for the
	 * <a href="https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html">{@code java.io.Serializable}</a>
	 * interface for more info.
	 */
	private static final long serialVersionUID = 1L;
	// endregion

	// region Constructors
	private SessionManager()
	{
		SessionManager deserializedSessionManager = null;

		// Deserialize SessionManager file:
		File sessionManagerFile = new File(Constants.SESSION_MANAGER_FILE);

		if (sessionManagerFile.exists())
		{
			try
			{
				FileInputStream fileInputStream = new FileInputStream(sessionManagerFile);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

				deserializedSessionManager = (SessionManager)objectInputStream.readObject();

				objectInputStream.close();
				fileInputStream.close();
			}
			catch (IOException ex)
			{
				// TODO
				ex.printStackTrace();
			}
			catch (ClassNotFoundException ex)
			{
				// TODO
				ex.printStackTrace();
			}

			// Transfer properties:
			if (deserializedSessionManager != null)
			{
				this.latestSessionId = deserializedSessionManager.getLatestSessionId();
				this.previousSession = deserializedSessionManager.getPreviousSession();
				this.currentSession = deserializedSessionManager.getCurrentSession();
			}
		}
		else
		{
			System.err.printf("No serialized SessionManager file found at %s; creating empty SessionManager.\n",
				Constants.SESSION_MANAGER_FILE);
		}
	}
	// endregion

	// region Methods
	/**
	 * Gets the singleton {@code SessionManager} instance, creating a new one if the
	 * one does not already exist.
	 * @return A {@code SessionManager} object.
	 */
	@NotNull
	public static SessionManager getInstance()
	{
		// Create the singleton instance if it doesn't exist:
		if (instance == null)
		{
			instance = new SessionManager();
		}

		return instance;
	}

	/**
	 * Gets the current session.
	 * @return A {@code Session} object referring to the user's current session.
	 */
	public Session getCurrentSession()
	{
		return this.currentSession;
	}

	/**
	 * Gets the previous session.
	 * @return A {@code Session} object referring to the user's previous session,
	 * or {@code null} if there is no previous session..
	 */
	public Session getPreviousSession()
	{
		return this.previousSession;
	}

	/**
	 * Gets the latest session ID.
	 * @return A {@code Session} object referring to the user's previous session.
	 */
	public int getLatestSessionId()
	{
		return latestSessionId;
	}

	/**
	 * Sets the previous session.
	 */
	public void setPreviousSession(Session previousSession)
	{
		this.previousSession = previousSession;
	}

	/**
	 * Sets the current session.
	 */
	public void setCurrentSession(Session currentSession)
	{
		this.currentSession = currentSession;
	}

	/**
	 * Creates a new Session with the specified parameters.
	 * @return A new {@code Session} object.
	 */
	@NotNull
	public Session createSession()
	{
		// Increment latest sesson ID:
		this.latestSessionId++;
		final int sessionId = this.latestSessionId;

		// Generate database file name:
		final String databaseFileName = Paths.get(Constants.SESSIONS_DIRECTORY, Integer.toString(sessionId)).toString();

		return new Session(sessionId, new DatabaseHandler(databaseFileName));
	}

	/**
	 * Internal-use read method for deserialization.
	 */
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException
	{
		// Default object deserialization:
		stream.defaultReadObject();
	}

	/**
	 * Internal-use write method for serialization.
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException
	{
		// Default object serialization:
		stream.defaultWriteObject();
	}

	public void serialize()
	{
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(Constants.SESSION_MANAGER_FILE);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(this);

			objectOutputStream.flush();
			fileOutputStream.flush();

			objectOutputStream.close();
			fileOutputStream.close();
		}
		catch (IOException ex)
		{
			// TODO
			ex.printStackTrace();
		}
	}
	// endregion
}
