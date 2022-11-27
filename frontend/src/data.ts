interface pluginData {
    coreData: subdata[]
    locationFreq: {}
}

interface subdata {
    lng: number
    lat: number
    location: string
    time: string
    text: string    
}

export type { pluginData }