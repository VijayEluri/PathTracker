package com.ilikeblues.android.pathTracker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.ilikeblues.android.pathTracker.PathTracker.Notes;

public class FullSummary extends Activity {

	public static final int MENU_ITEM_COPY  = Menu.FIRST;
	public static final int MENU_ITEM_EMAIL  = Menu.FIRST + 1;
	
	public FullSummary() {
		// TODO Auto-generated constructor stub
	}
	
	private static final String TAG = "FullSummary";

	private TextView fullTextView = null;
    /**
     * The columns we are interested in from the database
     */
    private static final String[] PROJECTION = new String[] {
        Notes._ID, // 0
        Notes.NOTE, // 1
        Notes.CREATED_DATE, // 2
        Notes.LOCATION, // 3
        Notes.LATITUD, // 4
        Notes.LONGITUD, // 5
    };

    /** The index of the title column */
    private static final int COLUMN_INDEX_TITLE = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);

        // If no data was given in the intent (because we were started
        // as a MAIN activity), then use our default content provider.
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Notes.CONTENT_URI);
        }

        // Inform the list we provide context menus for items
        fullTextView = (TextView) findViewById(R.id.FSTextView);
        fullTextView.setOnCreateContextMenuListener(this);
        
        // Perform a managed query. The Activity will handle closing and requerying the cursor
        // when needed.
        Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null, null,
                Notes.DEFAULT_SORT_ORDER);

        // Used to map notes entries from the database to views
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.noteslist_item, cursor,
//                new String[] { Notes.TITLE, Notes.LOCATION }, new int[] { android.R.id.text1, android.R.id.text2 });
//        
//        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info;
        try {
             info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            Log.e(TAG, "bad menuInfo", e);
            return;
        }

        Cursor cursor = (Cursor) fullTextView;
        if (cursor == null) {
            // For some reason the requested item isn't available, do nothing
            return;
        }

        // Setup the menu header
        menu.setHeaderTitle(cursor.getString(COLUMN_INDEX_TITLE));

        // Add a menu item to delete the note
        menu.add(0, MENU_ITEM_COPY, 0, R.string.menu_copy);
        menu.add(0, MENU_ITEM_EMAIL, 1, R.string.menu_email);
    }
        
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        
        try {
             info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        } catch (ClassCastException e) {
            Log.e(TAG, "bad menuInfo", e);
            return false;
        }

        switch (item.getItemId()) {
            case MENU_ITEM_COPY: {
                // Delete the note that the context menu is for
                ((ClipboardManager)getSystemService(CLIPBOARD_SERVICE))
                				  .setText(fullTextView.getText());
                
                Toast t = new Toast(this);
                t.setText(R.string.text_copied);
                
                t.show();
                return true; 
            }
            case MENU_ITEM_EMAIL: { 
                // Delete the note that the context menu is for
                // start email activity:
                return true;
            }
        }
        return false;
    }
}
