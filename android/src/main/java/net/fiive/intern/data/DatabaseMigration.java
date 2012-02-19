package net.fiive.intern.data;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseMigration {

	public int getVersion();
	public void execute(SQLiteDatabase db);


}
