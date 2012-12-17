package org.stackoverflow.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import org.stackoverflow.client.places.AskQuestionPlace;
import org.stackoverflow.client.places.LoginPlace;
import org.stackoverflow.client.places.SearchPlace;
import org.stackoverflow.client.places.ViewQuestionPlace;


/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers( {LoginPlace.Tokenizer.class, SearchPlace.Tokenizer.class, AskQuestionPlace.Tokenizer.class, ViewQuestionPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
