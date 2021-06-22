package Ozeki.Libs.Rest

class Configuration {
    public val Username : String
    public val Password : String
    public val ApiUrl : String

    constructor(username:String, password:String, apiurl:String) {
        this.Username = username
        this.Password = password
        this.ApiUrl = apiurl
    }
}