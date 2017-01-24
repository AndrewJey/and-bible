package net.bible.android.control;

import net.bible.android.control.backup.BackupControl;
import net.bible.android.control.bookmark.BookmarkControl;
import net.bible.android.control.comparetranslations.CompareTranslationsControl;
import net.bible.android.control.download.DownloadControl;
import net.bible.android.control.footnoteandref.FootnoteAndRefControl;
import net.bible.android.control.link.LinkControl;
import net.bible.android.control.navigation.DocumentBibleBooksFactory;
import net.bible.android.control.navigation.NavigationControl;
import net.bible.android.control.page.PageControl;
import net.bible.android.control.page.window.WindowControl;
import net.bible.android.control.readingplan.ReadingPlanControl;
import net.bible.android.control.search.SearchControl;
import net.bible.android.control.speak.SpeakControl;
import net.bible.android.control.versification.BibleTraverser;
import net.bible.android.view.activity.navigation.biblebookactionbar.BibleBookActionBarManager;
import net.bible.android.view.activity.navigation.biblebookactionbar.SortActionBarButton;
import net.bible.android.view.activity.readingplan.actionbar.ReadingPlanActionBarManager;
import net.bible.android.view.activity.search.searchresultsactionbar.ScriptureToggleActionBarButton;
import net.bible.android.view.activity.search.searchresultsactionbar.SearchResultsActionBarManager;
import net.bible.android.view.activity.speak.actionbarbuttons.SpeakActionBarButton;
import net.bible.android.view.activity.speak.actionbarbuttons.SpeakStopActionBarButton;
import net.bible.service.sword.SwordContentFacade;

import dagger.Component;

/**
 * Dagger Component to expose application scoped dependencies.
 *
 * @author Martin Denham [mjdenham at gmail dot com]
 * @see gnu.lgpl.License for license details.<br>
 * The copyright to this program is held by it's author.
 */
@ApplicationScope
@Component(modules=ControllerModule.class)
public interface ControllerComponent {

	//Exposed to sub-graphs.
	Initialisation initialisation();

	SwordContentFacade swordContentFacade();
	BibleTraverser bibleTraverser();
	NavigationControl navigationControl();
	DocumentBibleBooksFactory documentBibleBooksFactory();
	WindowControl windowControl();
	LinkControl linkControl();

	BackupControl backupControl();
	BookmarkControl bookmarkControl();
	DownloadControl downloadControl();
	PageControl pageControl();
	ReadingPlanControl readingPlanControl();
	SearchControl searchControl();
	CompareTranslationsControl compareTranslationsControl();
	FootnoteAndRefControl footnoteAndRefControl();

	SpeakControl speakControl();

	SortActionBarButton sortActionBarButton();
	SpeakActionBarButton speakActionBarButton();
	SpeakStopActionBarButton speakStopActionBarButton();
	ScriptureToggleActionBarButton scriptureToggleActionBarButton();
	ReadingPlanActionBarManager readingPlanActionBarManager();
	SearchResultsActionBarManager searchResultsActionBarManager();
	BibleBookActionBarManager bibleBookActionBarManager();
}