import React      from 'react';
import ReactDOM   from 'react-dom';
import {
  renderIntoDocument,
  scryRenderedDOMComponentsWithClass,
  isCompositeComponent,
  Simulate
}                 from 'react-addons-test-utils';
import {expect}   from 'chai';
import {spy}      from 'sinon';

import Icon       from './Icon.jsx';

describe('Icon', () => {

  it('should be a React component', () => {
    const component = renderIntoDocument( <Icon type={'car'}/> );
    expect(isCompositeComponent(component)).to.be.true;
  });

});
