package net.fiive.intern.data;

import android.database.sqlite.SQLiteDatabase;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;


public class MigrationManagerTest {

	private SQLiteDatabase db = Mockito.mock(SQLiteDatabase.class);

	@Test
	public void shouldBeAbleToCreateDatabase() {
		DatabaseMigration createMigrationMock = Mockito.mock(DatabaseMigration.class);
		MigrationManager manager = new MigrationManager(createMigrationMock);

		manager.createDatabase(db);
		Mockito.verify(createMigrationMock).execute(db);
		Mockito.verifyNoMoreInteractions(createMigrationMock);

		manager.upgradeDatabase(db, 0, 100);
	}

	@Test
	public void testDatabaseUpdate_oneMigration_rightVersion() {
		DatabaseMigration createMigrationStub = Mockito.mock(DatabaseMigration.class);
		DatabaseMigration upgradeMigrationMock = Mockito.mock(DatabaseMigration.class);
		Mockito.when(upgradeMigrationMock.getVersion()).thenReturn(1);
		MigrationManager manager = new MigrationManager(createMigrationStub, upgradeMigrationMock);

		manager.upgradeDatabase(db, 0, 100);
		Mockito.verify(upgradeMigrationMock).execute(db);
	}

	@Test
	public void testDatabaseUpdate_oneMigration_wrongVersion() {
		DatabaseMigration createMigrationStub = Mockito.mock(DatabaseMigration.class);
		DatabaseMigration upgradeMigrationMock = Mockito.mock(DatabaseMigration.class);
		Mockito.when(upgradeMigrationMock.getVersion()).thenReturn(2);
		MigrationManager manager = new MigrationManager(createMigrationStub, upgradeMigrationMock);

		manager.upgradeDatabase(db, 0, 1);
		Mockito.verify(upgradeMigrationMock, Mockito.times(2)).getVersion();
		Mockito.verifyNoMoreInteractions(upgradeMigrationMock);

	}

	@Test
	public void testDatabaseUpgrade_migrationLimits() {
		DatabaseMigration createMigrationStub = Mockito.mock(DatabaseMigration.class);
		DatabaseMigration upgradeMigration = Mockito.mock(DatabaseMigration.class);
		Mockito.when(upgradeMigration.getVersion()).thenReturn(1,2,3,4,1,2,3,4);

		MigrationManager manager = new MigrationManager(createMigrationStub, upgradeMigration, upgradeMigration, upgradeMigration, upgradeMigration);
		manager.upgradeDatabase(db, 1, 3);
		Mockito.verify(upgradeMigration, Mockito.times(2)).execute(db);
	}

	@Test
	public void testDatabaseUpgrade_migrationWithOldVersionSameAsNewVersion() {
		DatabaseMigration create = Mockito.mock(DatabaseMigration.class);
		DatabaseMigration upgrade = Mockito.mock(DatabaseMigration.class);
		Mockito.when(upgrade.getVersion()).thenReturn(1);

		MigrationManager manager = new MigrationManager(create, upgrade);
		manager.upgradeDatabase(db, 1, 1);
		Mockito.verify(upgrade, Mockito.times(2)).getVersion();
		Mockito.verifyNoMoreInteractions(upgrade);
	}

	@Test
	public void testDatabaseUpgrade_wrongLowerVersion() {
		DatabaseMigration create = Mockito.mock(DatabaseMigration.class);
		MigrationManager manager = new MigrationManager(create);
		try {
			manager.upgradeDatabase(db, -1, 0);
			Assert.fail();
		} catch( IllegalArgumentException exception ) {
			Assert.assertEquals(exception.getMessage(), "Error: Old version should be greater or equal than zero");
		}
	}

	@Test
	public void testDatabaseUpgrade_wrongNewerVersion() {
		DatabaseMigration create = Mockito.mock(DatabaseMigration.class);
		MigrationManager manager = new MigrationManager(create);

		try {
			manager.upgradeDatabase(db, 0, -1);
			Assert.fail();
		} catch( IllegalArgumentException exception) {
			Assert.assertEquals( exception.getMessage(), "Error: New version should be greater or equal than Old Version");
		}
	}

	@Test
	public void testManagerCreation_nullCreateMigration() {
		try {
			new MigrationManager(null);
			Assert.fail();
		} catch( NullPointerException exception ) {
			Assert.assertEquals( exception.getMessage(), "Error: createMigration cannot be null");
		}
	}

	@Test
	public void testManagerCreation_nullUpgradeMigration() {
		DatabaseMigration create = Mockito.mock(DatabaseMigration.class);
		try {
			new MigrationManager(create, new DatabaseMigration[] {null});
			Assert.fail();
		} catch( NullPointerException exception ) {
			Assert.assertEquals( exception.getMessage(), "Error: upgradeMigrations cannot contain null element");
		}
	}

	@Test
	public void testManagerCreation_migrationWithNegativeVersion() {
		DatabaseMigration create = Mockito.mock(DatabaseMigration.class);
		DatabaseMigration upgrade = Mockito.mock(DatabaseMigration.class);
		Mockito.when(upgrade.getVersion()).thenReturn(-1);
		Mockito.when(upgrade.toString()).thenReturn("migration");

		try {
			new MigrationManager(create, upgrade);
			Assert.fail();
		} catch( IllegalArgumentException exception ) {
			Assert.assertEquals( exception.getMessage(), "Error: Migration migration must not have version less than zero");
		}
	}
}
