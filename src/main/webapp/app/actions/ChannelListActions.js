import {GET_CHANNELS} from "../constants/ChannelList";

export const getChannels = (channels) => {
    return {
        type: GET_CHANNELS,
        channels
    }
};