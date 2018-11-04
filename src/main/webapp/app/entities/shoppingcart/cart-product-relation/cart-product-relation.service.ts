import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';

type EntityResponseType = HttpResponse<ICartProductRelation>;
type EntityArrayResponseType = HttpResponse<ICartProductRelation[]>;

@Injectable({ providedIn: 'root' })
export class CartProductRelationService {
  public resourceUrl = SERVER_API_URL + 'api/cart-product-relations';

  constructor(private http: HttpClient) {}

  create(cartProductRelation: ICartProductRelation): Observable<EntityResponseType> {
    return this.http.post<ICartProductRelation>(this.resourceUrl, cartProductRelation, { observe: 'response' });
  }

  update(cartProductRelation: ICartProductRelation): Observable<EntityResponseType> {
    return this.http.put<ICartProductRelation>(this.resourceUrl, cartProductRelation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICartProductRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICartProductRelation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
