import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {environment} from "../../../environments/environment";
import { UsersService } from './users.service';

describe('UsersService', () => {
  let service: UsersService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UsersService]
    });
    service = TestBed.inject(UsersService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Verify that there are no outstanding HTTP requests after each test
    httpMock.verify();
  });
  
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should retrieve data from the backend API via GET', () => {
    // Define the mock response data
    const mockData = [
      {
        "id": "24eb63e7-d14a-4a59-99f1-8641d1f7bf08",
        "username": "oleas40",
        "email": "sorocoletnii.olga@gmail.com",
        "password": "xxxx",
        "userRole": [
            "USER",
            "CONTENT_MAKER"
        ],
        "parentId": "6c115f3f-8b98-4a19-b3ae-a91c78134245"
    },
    {
        "id": "3263d5cf-3907-40e0-85ce-646a3f599a89",
        "username": "admin",
        "email": "admin@gmail.com",
        "password": "admin",
        "userRole": [
            "ADMIN"
        ],
        "parentId": "3c2aaf1e-dd25-4061-97c0-eb7482376569"
    },
    {
        "id": "55c2c5cd-a232-453d-84a9-a48e25333902",
        "username": "lara",
        "email": "larisa.mihai@gmail.com",
        "password": "xxxx",
        "userRole": [
            "USER"
        ],
        "parentId": "820bbc8c-f4c3-4dc1-af8f-f88b897a56c7"
    },
    {
        "id": "872f056f-d79b-4163-8165-dd7977121964",
        "username": "dev",
        "email": "dev@gmail.com",
        "password": "dev",
        "userRole": [
            "ADMIN"
        ],
        "parentId": "b9fb60b6-a831-44e9-8247-7c5b93b253e8"
    },
    {
        "id": "ca4cac89-523e-4aa6-a201-98aa95327b98",
        "username": "dorina",
        "email": "dorina.baca@gmail.com",
        "password": "xxxx",
        "userRole": [
            "USER"
        ],
        "parentId": "8f707477-c2dc-4a9e-964b-cea562d6971c"
    }
    ];

    // Call the getAll() method on the service and subscribe to the response
    service.getAllUsers().subscribe((data) => {
      // Assert that the response data matches the mock data
      expect(data).toEqual(mockData);
    });

    // Expect an HTTP GET request to the backend API
    const request = httpMock.expectOne(`${environment.apiUrl}users`);

    // Assert that the request method is GET
    expect(request.request.method).toEqual('GET');

    // Respond to the request with the mock data
    request.flush(mockData);
  });

  it('should retrieve a user by ID', () => {
    const testUserId = '24eb63e7-d14a-4a59-99f1-8641d1f7bf08';
    const testUser = { id: testUserId, name: 'Test User' };

    // Call the service's getById() method
    service.getUser(testUserId).subscribe((user) => {
      expect(user).toEqual(testUser);
    });

    // Set up a mock HTTP request to return the test user
    const req = httpMock.expectOne(`${environment.apiUrl}users/${testUserId}`);
    expect(req.request.method).toEqual('GET');
    req.flush(testUser);
  });

  it('should create a user', () => {
    const testUser = { id:'fb08ea67-7719-406a-b21e-dfccfc6db2ee', 
    username: 'Test User', 
    email: 'testUser@gmail.com', 
    password: 'password' };

    // Call the service's create() method
    service.createUser(testUser).subscribe((user) => {
      expect(user.id).toBeTruthy();
      expect(user.username).toEqual(testUser.username);
      expect(user.email).toEqual(testUser.email);
      expect(user.password).toEqual(testUser.password);
    });

    // Set up a mock HTTP request to return the newly created user
    const req = httpMock.expectOne(`${environment.apiUrl}users`);
    expect(req.request.method).toEqual('POST');
    req.flush({ ...testUser, id: 'fb08ea67-7719-406a-b21e-dfccfc6db2ee'});
  });

  it('should update a user', () => {
    const testUserId = 'fb08ea67-7719-406a-b21e-dfccfc6db2ee';
    const testUser = { id: testUserId, username: 'Test User Updated',email: 'testUser@gmail.com', 
    password: 'password' };

    // Call the service's update() method
    service.updateUser(testUserId, testUser).subscribe((user) => {
      expect(user.id).toBeTruthy();
      expect(user.username).toEqual(testUser.username);
      expect(user.email).toEqual(testUser.email);
      expect(user.password).toEqual(testUser.password);
    });

    // Set up a mock HTTP request to return the updated user
    const req = httpMock.expectOne(`${environment.apiUrl}users/${testUserId}`);
    expect(req.request.method).toEqual('PUT');
    req.flush(testUser);
  });

  it('should delete a user', () => {
    const testUserId = 'fb08ea67-7719-406a-b21e-dfccfc6db2ee';

    // Call the service's delete() method
    service.deleteUser(testUserId).subscribe();

    // Set up a mock HTTP request to return a successful response
    const req = httpMock.expectOne(`${environment.apiUrl}users/${testUserId}`);
    expect(req.request.method).toEqual('DELETE');
    req.flush(null, { status: 204, statusText: 'No Content' });
  });
});
