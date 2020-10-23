import { Component } from '@angular/core';
import { ENV } from '../../../../../../environments/environment';
import { VERSION } from '../../../../../../environments/version';

@Component({
    selector: 'navigation-bar',
    moduleId: module.id.toString(),
    templateUrl: 'navigation-bar.component.html'
})
export class NavigationBar {

    private _isThemeDefault = true;

    
    constructor() { }

    public get version(): string {
        return VERSION.NUMBER + ' (' + ENV.TYPE + ')';
    }

    public get isThemeDefault(): boolean {
        return this._isThemeDefault;
    }

    public changeTheme() {
        var current_theme = document.getElementById("current-theme");
        var theme = document.getElementById("theme-pack");
        if(current_theme.getAttribute("data") == "default"){
            this._isThemeDefault = false;
            current_theme.setAttribute("data", "dark")
            theme.setAttribute("href", "https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/cyborg/bootstrap.min.css");
        }
        else{
            this._isThemeDefault = true;
            current_theme.setAttribute("data", "default")
            theme.setAttribute("href", "https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/yeti/bootstrap.min.css");
        }
    }
    
    ngOnInit() {
        var theme = document.getElementById("theme-pack");

        if(this.isThemeDefault){
            theme.setAttribute("href", "https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/yeti/bootstrap.min.css");
        }
        else{
            theme.setAttribute("href", "https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/cyborg/bootstrap.min.css");
        }    
    }
}
