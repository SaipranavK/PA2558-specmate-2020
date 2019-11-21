import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SpecmateSharedModule } from '../../../../../specmate/specmate.shared.module';
import { MaximizeButtonModule } from '../maximize-button/maximize-button.module';
import { ToolPalletteModule } from '../tool-pallette/tool-pallette.module';
import { GraphicalEditor } from './components/graphical-editor.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    MaximizeButtonModule,
    ToolPalletteModule,
    SpecmateSharedModule,
    BrowserModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    GraphicalEditor
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    GraphicalEditor
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class GraphicalEditorModule { }
