import React from "react";
import {Previews} from "@amplicode/ide-toolbox";
import {PaletteTree} from "./palette";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
        </Previews>
    );
};

export default ComponentPreviews;
