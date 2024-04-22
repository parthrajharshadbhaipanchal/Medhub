import API from "../api";
import Cookies from "js-cookie";

// get all details of the patient
export const changePassword = async (data) => {
    const res = await API.post(`/doctor/change_password`,data
    )
        .then((res) => {

            const response = {
                message: res.data.message,
                status: res.data.isSuccess ? "success" : "error",
                isSuccess: res.data.isSuccess,
            };
            console.log(response);
            return response;
        })
        .catch((err) => {
            console.log(err);
            return err;
        });

    return res;
};
