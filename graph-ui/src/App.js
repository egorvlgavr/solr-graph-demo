import React, {Component} from 'react';
import {Graphviz} from 'graphviz-react';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'

class App extends Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            depts: [],
            selectedDept: null,
            dot: null,
            json: null,
            error: null,
            isLoaded: false
        };

        this.handleShow = this.handleShow.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        fetch('/api/departments')
            .then(res => res.json())
            .then((result) => {
                this.setState({
                    depts: result,
                    selectedDept: result[0]
                    }
                );
            },
                (error) => {
                    console.log(error);
                    this.setState({isLoaded: false})
                })
    }

    handleChange(e) {
        this.setState({selectedDept: e.target.value})
    }

    handleShow() {
        fetch('/api/category/' + this.state.selectedDept)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        dot: result.dot,
                        json: JSON.stringify(result.graph, null, '  ')
                    });
                },
                (error) => {
                    console.log(error);
                    this.setState({isLoaded: false})
                }
            );
    }

    render() {
        const options = this.state.depts.map(dept => {
            return <option key={dept}>{dept}</option>
        });

        return (
            <Container>
                <h3>Solr categories graph</h3>
                <Form>
                    <Form.Group controlId="graphSelector">
                        <Form.Label>Select graph to show</Form.Label>
                        <Form.Control as="select" onChange={this.handleChange}>
                            {options}
                        </Form.Control>
                    </Form.Group>

                </Form>
                <Button onClick={this.handleShow}>
                    Show
                </Button>
                {this.state.isLoaded &&
                    <Row>
                        <Col>
                            <h4>Graph</h4>
                            <Graphviz dot={this.state.dot}/>
                        </Col>

                        <Col>
                            <h4>Json</h4>
                            <div id="json">
                            <pre>
                                {this.state.json}
                            </pre>
                            </div>
                        </Col>
                    </Row>
                }
            </Container>
        );
    }
}

export default App;
