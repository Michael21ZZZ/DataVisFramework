import { fstat } from "fs";
import { VisualizationPlugin } from "./plugin";
import fs from 'fs';


function loadPlugin(): VisualizationPlugin[] {
    let MyClasses: string[] = [];
    let plugins: VisualizationPlugin[] = [];
    fs.readdirSync('./plugins/').forEach((fuck: string) => {
        MyClasses.push(fuck)
    })
    //this.getMyClassFiles() return all the filenames of the classes in the directory
    for (const file of MyClasses) {
        const myClass = require(file);
        plugins.push(new myClass.outClass());
    }
    return plugins;
}

export {loadPlugin}