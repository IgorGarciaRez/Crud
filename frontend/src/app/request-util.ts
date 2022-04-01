import { HttpParams } from "@angular/common/http";

export class RequestUtil {

  public static buildParams(data: any) {
    let httpParams = new HttpParams();
    Object.keys(data).forEach(function (key) {
        let isOk = false;
        if (data[key] !== undefined && data[key] !== null) {
            isOk = true;
            if (data[key] instanceof Array) {
                if (data[key].length === 0) {
                    isOk = false;
                }
            }
            if(data[key] instanceof Date) {
                let date: Date = data[key];
                date.setUTCHours(date.getHours());
                data[key] = date.toISOString();
            }
        }
        if (isOk) {
            httpParams = httpParams.append(key, data[key]);
        }
    });
    return httpParams;
  }

  public static buildOptions(data: any) {
      return {params: this.buildParams(data)};
  }
}
