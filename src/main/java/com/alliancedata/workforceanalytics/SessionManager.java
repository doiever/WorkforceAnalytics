package com.alliancedata.workforceanalytics;

import com.alliancedata.workforceanalytics.models.DatabaseHandler;
import com.alliancedata.workforceanalytics.models.Session;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 *  Implementation of the system's Session Manager.
 *  The session manager remembers the user's current {@code Session} and previous {@code Session}.
 *  It also allows for the creation, selection, and deletion of {@code Session}s.
 */
public class SessionManager implements Serializable
{
    // Fields:
    private static final ObjectProperty<Session> currentSession = new SimpleObjectProperty<>();
    private static final ObjectProperty<Session> previousSession= new SimpleObjectProperty<>();


    // Properties:
    @NotNull
    public static ObjectProperty<Session> currentSessionProperty()
    {
        return currentSession;
    }

    @NotNull
    public static ObjectProperty<Session> previousSessionProperty()
    {
        return previousSession;
    }

    // Methods:
    @NotNull
    public static Session getCurrentSession()
    {
        return currentSession.getValue();
    }

    @NotNull
    public static Session getPreviousSession()
    {
        return previousSession.getValue();
    }

    @NotNull
    public static Session createSession(String name, String databaseFileName)
    {
        // TODO: Ensure unique ID across sessions
        int id = -1;
        return new Session(name, id, new DatabaseHandler(databaseFileName));
    }
}
