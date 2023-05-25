import router from "@/router";

function processResError(error) {
  const { message } = error.response.data;
  switch (error.response.status) {
    case 500:
      router.replace({ name: "error", params: { message } });
      break;
    default:
      alert(message);
      break;
  }
}

export { processResError };
