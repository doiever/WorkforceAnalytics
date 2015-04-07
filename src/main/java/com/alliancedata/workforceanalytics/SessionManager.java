package com.alliancedata.workforceanalytics;

import com.alliancedata.workforceanalytics.models.DatabaseHandler;
import com.alliancedata.workforceanalytics.models.Session;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 *  Implementation of the system's Session Manager.
 *  The session manager remembers the user's current {@code Session} and previous {@code Session}.
 *  It also allows for the creation, selection, and deletion of {@code Session}s.
 */
public class SessionManager
{
    // Fields:
    private final ObjectProperty<Session> currentSession;
    private final ObjectProperty<Session> previousSession;

    // Constructors:
    public SessionManager()
    {
        this.currentSession = new SimpleObjectProperty<>();
        this.previousSession = new SimpleObjectProperty<>();
    }

    // Properties:
    @NotNull
    public ObjectProperty<Session> currentSessionProperty()
    {
        return this.currentSession;
    }

    @NotNull
    public ObjectProperty<Session> previousSessionProperty()
    {
        return this.previousSession;
    }

    // Methods:
    @NotNull
    public Session getCurrentSession()
    {
        return this.currentSession.getValue();
    }

    @NotNull
    public Session getPreviousSession()
    {
        return this.previousSession.getValue();
    }

    @NotNull
    public static Session createSession(String name, File databaseFile)
    {
        // TODO: Ensure unique ID across sessions
        int id = -1;
        return new Session(name, id, new DatabaseHandler(databaseFile));
    }
}
