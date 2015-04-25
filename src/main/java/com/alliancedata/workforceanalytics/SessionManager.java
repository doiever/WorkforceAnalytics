package com.alliancedata.workforceanalytics;

import com.alliancedata.workforceanalytics.models.DatabaseHandler;
import com.alliancedata.workforceanalytics.models.Session;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 *  Implementation of the system's Session Manager.
 *  The session manager remembers the user's current and previous Session.
 *  It also allows for the creation, selection, and deletion of {@code Session} objects,
 *  and maintains the latest session ID (akin to auto-incrementing database columns).
 */
public final class SessionManager implements Serializable
{
    // region Fields
    private static final ObjectProperty<Session> currentSession  = new SimpleObjectProperty<>();
    private static final ObjectProperty<Session> previousSession = new SimpleObjectProperty<>();
	private static final IntegerProperty latestSessionId = new SimpleIntegerProperty(0);
	// endregion

    // region Properties
	/**
	 * Gets the {@code currentSession} property.
	 * @return An {@code ObjectProperty<Session>} containing the current Session.
	 */
    @NotNull
    public static ObjectProperty<Session> currentSessionProperty()
    {
        return currentSession;
    }

	/**
	 * Gets the {@code previousSession} property.
	 * @return An {@code ObjectProperty<Session>} containing the previous Session.
	 */
    @NotNull
    public static ObjectProperty<Session> previousSessionProperty()
    {
        return previousSession;
    }

	/**
	 * Gets the {@code sessionId} property.
	 * @return A {@code IntegerProperty} containing the value of the latest session ID.
	 */
	@NotNull
	public static IntegerProperty latestSessionIdProperty()
	{
		return latestSessionId;
	}
	// endregion

    // region Methods
	/**
	 * Gets the current session.
	 * @return A {@code Session} object referring to the user's current session.
	 */
    @NotNull
    public static Session getCurrentSession()
    {
        return currentSession.getValue();
    }

	/**
	 * Gets the previous session.
	 * @return A {@code Session} object referring to the user's previous session.
	 */
    @NotNull
    public static Session getPreviousSession()
    {
        return previousSession.getValue();
    }

	/**
	 * Gets the latest session ID.
	 * @return A {@code Session} object referring to the user's previous session.
	 */
	public static int getLatestSessionId()
	{
		return latestSessionId.getValue();
	}

	/**
	 * Creates a new session with the specified parameters.
	 * @return A new {@code Session} object.
	 */
    @NotNull
    public static Session createSession(String name, String databaseFileName)
    {
        // TODO: Ensure unique ID across sessions
        int id = -1;
        return new Session(name, id, new DatabaseHandler(databaseFileName));
    }
	// endregion
}
