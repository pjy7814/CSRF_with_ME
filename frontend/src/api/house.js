import { houseInstance } from "./index.js";

const house = houseInstance();

function houseList(params, success, fail) {
  house.get(``, { params: params }).then(success).catch(fail);
}

export { houseList };
