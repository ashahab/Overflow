package config
/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 6/22/12
 * Time: 8:39 AM
 * To change this template use File | Settings | File Templates.
 */
class SearchResult {
  var _success:Boolean as success = false;
  var _json:String as json = "";
  function toString():String {
    return "success: " + this.success + " json: " + this.json;
  }
}