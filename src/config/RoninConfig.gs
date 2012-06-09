package config

uses ronin.*
uses ronin.config.*
uses ronin.console.*
uses ronin_coffee.CoffeeFilter


class RoninConfig extends DefaultRoninConfig {


  /* Set up your RoninConfig as you see fit */
  construct(m : ApplicationMode, an : RoninServlet){
    super(m, an)
//    Filters.add( new CoffeeFilter(){ :Cache = m != DEVELOPMENT } )
    //DefaultController = controller.MyDefaultController
//    var node = NodeBuilder.nodeBuilder().client(true).node();

    if(m == DEVELOPMENT) {
      AdminConsole.start()
      db.model.Database.JdbcUrl = "jdbc:h2:file:runtime/h2/devdb"
    } else if( m == TESTING ) {
      db.model.Database.JdbcUrl = "jdbc:h2:file:runtime/h2/testdb"
    } else if( m == STAGING ) {
      db.model.Database.JdbcUrl = "jdbc:h2:file:runtime/h2/stagingdb"
    } else if( m == PRODUCTION ) {
      db.model.Database.JdbcUrl = "jdbc:h2:file:runtime/h2/proddb"
    }
  }

}